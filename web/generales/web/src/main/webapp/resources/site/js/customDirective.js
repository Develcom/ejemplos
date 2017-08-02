customDirectives = angular.module('customDirectives', []);
customDirectives.directive('customPopover', function () {
    return {
        restrict: 'A',
        template: '<span>{{label}}</span>',
        link: function (scope, el, attrs) {
            scope.label = attrs.popoverLabel;
            $(el).popover({
                trigger: 'manual',
                html: true,
                title: '<b>' + attrs.popoverTittle + '</b>',
                content: '<span>' + attrs.popoverContenido + '</span>',
                placement: attrs.popoverPlacement
            }).bind("popoverValid", function(){
            	if(attrs.popoverValidation == "true"){
            		if($(this)[0] == null || $(this)[0].nextElementSibling == null || $(this)[0].nextElementSibling.className.indexOf('popover') == -1){
            			$(this).popover('show');
            		}
            	}else{
            		$(this).popover('hide');
            	}
            }).blur(function(){
            	$(this).popover('hide');
            }).click(function(){
            	if(attrs.popoverValidation == "true"){
            		if($(this)[0] == null || $(this)[0].nextElementSibling == null || $(this)[0].nextElementSibling.className.indexOf('popover') == -1){
            			$(this).popover('show');
            		}
            	}else{
            		$(this).popover('hide');
            	}
            	
            });
//            $(el).css("z-index","5000");
        }
    };
});
customDirectives.directive('customHeight', function () {
	return function (scope, element, attrs) {
        element.height($scope.heightPerformPeticionDatos);
    }
});
