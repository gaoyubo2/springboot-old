HROS.folderThumbnail = (function(){
	return {
		init: function(){
            $('body').on('click', '.quick_view_container', function(){
				HROS.popupMenu.hide();
			}).on('click', '.quick_view_container_open', function(){
				HROS.window.create($(this).parents('.quick_view_container').attr('appid'), 'folder');
			}).on('click', '.appbtn', function(){
				HROS.popupMenu.hide();
			});
		},
		get: function(obj){
			setTimeout(function(){
				//判断文件夹窗口是否已打开
				var iswindowopen = false;
				$('body .quick_view_container2').each(function(){
					// $(this).remove()
                    // HROS.folderThumbnail.hide()
				});
				if(iswindowopen){
					var folderViewId = '#qv_' + obj.attr('appid');
				}else{
					// HROS.folderThumbnail.hide();
				}
				var sc = '';
				$(HROS.VAR.folder).each(function(){
					if(this.appid == obj.attr('appid')){
						sc = this.apps;
						return false;
					}
				});
				var folderViewHtml = '', height = 0;
				if(sc != ''){
					$(sc).each(function(){
						folderViewHtml += HROS.template.app({
							'title': this.name,
							'type': this.type,
							'id': 'd_' + this.appid,
							'appid': this.appid,
							'realappid': this.realappid,
							'imgsrc': this.icon,
                            //ww修改
							'appsize': 12
						});
					});
					if(sc.length % 4 == 0){
						height += Math.floor(sc.length / 4) * 60;
					}else{
						height += (Math.floor(sc.length / 4) + 1) * 60;
					}
				}else{
					folderViewHtml = '';
					height += 30;
				}
				//判断是桌面上的文件夹，还是应用码头上的文件夹
				var left, top;
				if(obj.parent('div').hasClass('dock-applist')){
					left = parseInt(obj.attr('left')) + 200000;
					top = parseInt(obj.attr('top')) + 200000;
				}else{
                    //ww修改
					left = parseInt(obj.attr('left'))+200000;
					top = parseInt(obj.attr('top'))+200000 ;
				}
                //预览居右
                $('body').append(HROS.template.folderThumbnail({
                    'id': 'qv2_' + obj.attr('appid'),
                    'appid': obj.attr('appid'),
                    'realappid': obj.attr('realappid'),
                    'apps': folderViewHtml,
                    'top': top,
                    'left': left,
                    'height': height,
                    'mlt': obj.offset().top - top,
                    'mlm': true,
                    'mlb': height + 24 - (obj.offset().top - top) - 20,
                    'mrt': Math.ceil((height + 24) / 2),
                    'mrm': false,
                    'mrb': Math.ceil((height + 24) / 2)
                }));
			}, 0);
		},
        hide: function(){   
			$('.quick_view_container2').remove();
		}
	}
})();