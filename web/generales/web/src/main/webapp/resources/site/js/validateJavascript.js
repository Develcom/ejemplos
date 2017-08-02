//window.onbeforeunload = function() { 
//	alert("You work will be lost."); 
//};
//
//
//function preventBack(){window.history.forward();}
//setTimeout("preventBack()", 0);
//alert("going back");
//window.onunload=function(){null};

// $(document).ready(function() {
//    function disableBack(e) { 
////    	e.preventDefault;
//    	window.history.forward();
////    	alert("going back");
//    }
//    window.onunload=function(){null};
////    window.forward = function(){null};
//    window.onload = disableBack();
//    window.onpageshow = function(evt) { if (evt.persisted) disableBack() }
//});

$(document).ready(function() {
    function disableBack() { window.history.forward() }

    window.onload = disableBack();
    window.onpageshow = function(evt) { if (evt.persisted) disableBack() }
});