// 해당 url로 이동
function goUrl(url) {
	location.href = url;
}

// 검색 폼 체크
function searchCheck(form) {
	if (form.searchText.value == '') {
		alert('검색어를 입력하세요.');
		form.searchText.focus();
		return false;
	}
	form.submit();
}

// 삭제 여부 확인 후 해당 url로 이동
function deleteCheck(url) {
	if (confirm('정말 삭제하시겠어요?')) {
		location.href = url;
	}
}

// 수정 폼 체크
function boardWriteCheck(form) {
	if (form.title.value.length == 0) {
		alert('제목을 입력하세요.');
		form.title.focus();
		return false;
	}
	if (form.writer.value.length == 0) {
		alert('이름을 입력하세요');
		form.writer.focus();
		return false;
	}
//	if (form.contents.value.length == 0) {
//		alert('내용을 입력하세요');
//		form.contents.focus();
//		return false;
//	}
	if (CKEDITOR.instances.contents.getData().length == 0) {
		alert('내용을 입력하세요');
		CKEDITOR.instances.contents.focus();
		return false;
	}
	form.submit();
}
// 폼 빌드가 비어있는지 여부를 체크하여 에러메시지를 출력
function checkNotEmpty(inputField, errorSpan) {
	if (inputField.value.length == 0) {
		errorSpan.innerHTML = "글쓴이 이름을 입력하세요.";
	} else {
		errorSpan.innerHTML = "";
	}

}