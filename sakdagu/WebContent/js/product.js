/**
 * 
 */
var addProduct = function() {
	var proNo = $('#proNo').attr('value');
	$('#proNo').attr('value', Number(proNo) + 1);
	var url1 = '<c:import url="products.jsp?proNo=상품 '.concat(proNo).concat(
			'"/>');
	$(url1).appendTo('#productArea');
};

var addOption = function() {
	var proNo = $('#proNo').attr('value');
	var optNo = $('#optNo').attr('value');
	$('#optNo').attr('value', Number(optNo) + 1);
	var url2 = '<c:import url="options.jsp?proNo=상품 '.concat(proNo).concat(
			'&optNo=옵션').concat(optNo).concat('"/>');
	$(url2).appendTo('#optionArea');
};


