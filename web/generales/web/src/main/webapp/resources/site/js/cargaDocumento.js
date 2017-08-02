App.directive('validFile', function () {
    return {
        require:'ngModel',
        link:function (scope, elem, attrs, ngModel) {
            var validFormats = ['jpg','pdf'];
            elem.bind('change', function () {
                validImage(false);
                scope.$apply(function () {
                    ngModel.$render();
                });

                var file = elem[0].files[0];

                if (file.size > 2000000) {
                    scope.errorsize="Debe ser menor a 2 Mb el tamaño del archivo";
                    scope.divErrorS="<div class='alert alert-warning row' role='alert'>" +
                        "<div class='col-sm-1'>" +
                        "<span style='transform: scale(1, 1);' class='glyphicon glyphicon-alert'>" +
                        "</span></div><p id='generico' class='col-sm-11'><i><b>NOTA: </b>" +scope.errorsize+
                        "</i></p></div>" ;
                    ngModel.$setValidity("length", false);

                } else {
                    scope.divErrorS="";
                    scope.errorsize="";
                    ngModel.$setValidity("length", true);

                }

            });
            ngModel.$render = function () {
                ngModel.$setViewValue(elem.val());
            };
            function validImage(bool) {
                ngModel.$setValidity('extension', bool);
            }

            ngModel.$parsers.push(function (value) {
                scope.errorformat="";  scope.divErrorF="";
                var ext = value.substr(value.lastIndexOf('.') + 1);
                if (ext == '') return;
                if (validFormats.indexOf(ext) == -1) {

                    scope.errorformat="El tipo de archivo debe ser jpg o pdf";
                    scope.divErrorF="<div class='alert alert-warning row' role='alert'>" +
                        "<div class='col-sm-1'>" +
                        "<span style='transform: scale(1, 1);' class='glyphicon glyphicon-alert'>" +
                        "</span></div><p id='generico' class='col-sm-11'><i><b>NOTA: </b>" +scope.errorformat+
                        "</i></p></div>" ;
                    return value;
                }
                validImage(true);
                return value;
            });
        }
    };
});