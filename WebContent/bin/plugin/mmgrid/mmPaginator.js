!function($){
    var MMPaginator = function(element, options){
        this.$el = $(element);
        this.opts = options;
    };

    MMPaginator.prototype = {
        _initLayout: function(){
            var that = this;
            var $el = this.$el;
            var opts = this.opts;

            $el.addClass("mmPaginator");
            var pgHtmls = [
                '<div class="totalCountLabel"></div>',
                '<ul class="pageList"></ul>',
                '<div class="limit"><select></select></div>'
            ];
            $el.append($(pgHtmls.join('')));

            this.$totalCountLabel = $el.find('.totalCountLabel');
            this.$pageList = $el.find('.pageList');
            this.$limitList = $el.find('.limit select');

            var $limitList = this.$limitList;
            $.each(opts.limitList, function(){
                var $option = $('<option></option>')
                    .prop('value',this)
                    .text(that.formatString(opts.limitLabel,[this]));
                $limitList.append($option);
            });

            $limitList.on('change', function(){
            	//2013-07-02 ljg start
            	//切换每页显示行数后,当前页设为首页
            	$el.data('page', 1);
            	//2013-07-02 ljg end
                that.$mmGrid.load();
            });

        }
    //ljg 2013-07-13 start
    	, setFirstPage: function(){
    		var $el = this.$el;
    		$el.data('page', 1);
    	}
    //ljg 2013-07-13 end 
        , _plain: function(page, totalCount, limit){
            var that = this;
            var $el = this.$el;
            var $pageList = this.$pageList;

            var totalPage = totalCount % limit === 0 ? parseInt(totalCount/limit) : parseInt(totalCount/limit) + 1;
            totalPage = totalPage ? totalPage : 0;
            if(totalPage === 0){
                page = 1;
            }else if(page > totalPage){
                page = totalPage;
            }else if(page < 1 && totalPage != 0){
                page = 1;
            }
            //
            var $prev = $('<li><a>«</a></li>');
            if(page<=1){
                $prev.addClass('disable');
            }else{
                $prev.find('a').on('click', function(){
                    $el.data('page', page-1);
                    that.$mmGrid.load();
                });
            }
            $pageList.append($prev);
            /////
            var list = [1];
            if(page > 4 ){
                list.push('...');
            }
            for(var i= 0; i < 5; i++){
                var no = page - 2 + i;
                if(no > 1 && no <= totalPage-1){
                    list.push(no);
                }
            }
            if(page+1 < totalPage-1){
                list.push('...');
            }
            if(totalPage>1){
                list.push(totalPage);
            }
            $.each(list, function(index, item){
                var $li = $('<li><a></a></li>');
                if(item === '...'){
                    $li.addClass('active').find('a').text('...');
                }else if(item === page){
                    $li.addClass('active').find('a').text(item);
                }else{
                    $li.find('a').text(item).prop('title','第'+item+'页').on('click', function(e){
                        $el.data('page', item);
                        that.$mmGrid.load();
                    });
                }
                $pageList.append($li);
            });
            //
            var $next = $('<li><a title="下一页">»</a></li>');
            if(page>=totalPage){
                $next.addClass('disable');
            }else{
                $next.find('a').on('click', function(){
                    $el.data('page', page+1);
                    that.$mmGrid.load();
                });
            }
            $pageList.append($next);
        }

        , _search: function(page, totalCount, limit){

        }
        //清除分页信息 2013-08-01 Start
        , clear: function(){
        	this.$pageList.empty();
        	var $el = this.$el;
        	var opts = this.opts;
        	var totalCount = 0;
        	$el.data('totalCount', totalCount);
        	this.$totalCountLabel.html(this.formatString(opts.totalCountLabel,[totalCount]));
        	var page = $el.data('page');
        	this._plain(page, totalCount, this.$limitList.val());
        }
        //清除分页信息 2013-08-01 End
        , load: function(params){
            var $el = this.$el;
            var $limitList = this.$limitList;
            var opts = this.opts;

            var page = params[opts.pageParamName];
            if(page === undefined){
                page = $el.data('page');
            }
            $el.data('page', page);

            var totalCount = params[opts.totalCountName];
            if(totalCount === undefined){
                totalCount = 0;
            }
            $el.data('totalCount', totalCount);
            
            //设置MMgrid总行数 ljg 2013-07-10 Start
            var that = this;
            that.$mmGrid.allRowCount = totalCount; //ljg 2013-07-11
            //设置MMgrid总行数 ljg 2013-07-10 End
            
            var limit = params[opts.limitParamName];
            if(limit === undefined){
                limit = $limitList.val();
            }
            this.$limitList.val(limit);

            this.$totalCountLabel.html(this.formatString(opts.totalCountLabel,[totalCount]));
            this.$pageList.empty();

            this._plain(page, totalCount, this.$limitList.val());
        }

        , formatString: function(text, args){
            return text.replace(/{(\d+)}/g, function(match, number) {
                return typeof args[number] != 'undefined'
                    ? args[number]
                    : match
                    ;
            });
        }

        , params: function(){
            var opts = this.opts;
            var $el = this.$el;
            var $limitList = this.$limitList;

            var params = {};
            params[opts.pageParamName] = $el.data('page');
            params[opts.limitParamName] = $limitList.val();
            return params;
        }

        , init: function($grid){
            var that = this;
            var opts = that.opts;
            this.$mmGrid = $grid;
            this._initLayout();
            this.$mmGrid.on('loadSuccess', function(e, data){
                that.load(data);
            });

            var params = {};
            params[opts.totalCountName] = 0;
            params[opts.pageParamName] = opts.page;
            params[opts.limitParamName] = opts.limit;
            this.load(params);

            if($grid.opts.indexCol){
                var indexCol = $grid.opts.cols[0];
                indexCol.renderer = function(val,item,rowIndex){
                    var params = that.params();
                    return '<label class="mmg-index">' +
                        (rowIndex + 1 + ((params[opts.pageParamName]-1) * params[opts.limitParamName])) +
                        '</label>';
                };
            }

        }

    };

    $.fn.mmPaginator = function(){

        if(arguments.length === 0 || typeof arguments[0] === 'object'){
            var option = arguments[0]
                , data = this.data('mmPaginator')
                , options = $.extend(true, {}, $.fn.mmPaginator.defaults, option);
            if (!data) {
                data = new MMPaginator(this[0], options);
                this.data('mmPaginator', data);
            }
            return $.extend(true, this, data);
        }
        if(typeof arguments[0] === 'string'){
            var data = this.data('mmPaginator');
            var fn =  data[arguments[0]];
            if(fn){
                var args = Array.prototype.slice.call(arguments);
                return fn.apply(data,args.slice(1));
            }
        }
    };

    $.fn.mmPaginator.defaults = {
         style: 'plain'
        , totalCountName: 'totalCount'
        , page: 1
        , pageParamName: 'page'
        , limitParamName: 'limit'
        , limitLabel: '每页{0}条'
        , totalCountLabel: '共<span>{0}</span>条记录'
        , limit: null
        , limitList: [20, 30, 40, 50]
    };

    $.fn.mmPaginator.Constructor = MMPaginator;

}(window.jQuery);