/**
 * 
 * 
 * 
 */
                $(document).ready(function() {inicializar1();}
                
                )


            inicializar1=function() {
//                $(document).ready(function() {
                    $('#open-wizard').click(function(e) {
                      e.preventDefault();
//                      $("#contenedor1").html('<a ng-click="click2(1)" href="#">Click me</a>');
//                      $("#contenedor1").scope().html='<a ng-click="click2(1)" href="#">Click me</a>';
                      console.log("cambiando scope......");
//                      $('#contenedor1').scope().html=$('#contenedor1').html();
                      console.log('cambiado el scope.....');
                      wizard.show();
                    });
               
                $.fn.wizard.logging = true;
                console.log('inicializando wizard');
                var wizard = $('#satellite-wizard').wizard({
                    keyboard : false,
                    contentHeight : 600,
                    contentWidth : 900,
//                    contentWidth : 1320,
                    backdrop: 'static'
                });
                console.log('wizard inicializado');

//                wizard.show();

                wizard.on('closed', function() {
                    wizard.reset();
                });

                wizard.on("reset", function() {
                    wizard.modal.find(':input').val('').removeAttr('disabled');
                    wizard.modal.find('.form-group').removeClass('has-error').removeClass('has-succes');
                    wizard.modal.find('#fqdn').data('is-valid', 0).data('lookup', 0);
                });

                wizard.on("submit", function(wizard) {
                    var submit = {
                        "hostname": $("#new-server-fqdn").val()
                    };
                    console.log('..................datos del function submit................');
                    this.log('seralize()');
                    this.log(this.serialize());
                    this.log('serializeArray()');
                    this.log(this.serializeArray());

                    setTimeout(function() {
                        wizard.trigger("success");
                        wizard.hideButtons();
                        wizard._submitting = false;
                        wizard.showSubmitCard("success");
                        wizard.updateProgressBar(0);
                    }, 2000);
                });
                wizard.on("next", (function(wizard){
                	console.log('........');
                    alert('hola wizard');
                }));

                wizard.el.find(".wizard-success .im-done").click(function() {
                    wizard.hide();
                    setTimeout(function() {
                        wizard.reset();
                    }, 250);

                });

                wizard.el.find(".wizard-success .create-another-server").click(function() {
                    wizard.reset();
                });


//                $('#open-wizard').click(function(e) {
//                    e.preventDefault();
//                    wizard.show();
//                });
            };
