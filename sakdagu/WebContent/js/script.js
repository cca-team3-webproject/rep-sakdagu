	
function removeCheck(url){
		if(confirm('정말로 탈퇴하시겠습니까?')){
			location.href = url;
		}
	}

function openDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			document.getElementById('zipcode1').value = data.postcode1;
			document.getElementById('zipcode2').value = data.postcode2;
			document.getElementById('addr').value = data.address;
		}
	}).open();
}

function passchk(form){
//	 var password = document.getElementById('password');
//	 var password2 = document.getElementById('passwoard2');
	
	 if (form.password2.length == 0 || form.password2.value == "") {
	  alert('비밀번호를 입력해주세요');

	  
	 } 
	 else if (form.password.value != form.password2.value) {
		 alert('패스워드를 재입력해주세요.');
		 form.password2.value = "";
		 form.password.value = "";
	 }
	 return;
	}
