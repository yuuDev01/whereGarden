'use strict';

	
const $cancelBtn = document.getElementById('cancelBtn');

//답글 작성 취소
$cancelBtn?.addEventListener("click", e=>{
	const category = e.target.dataset.category;
	if(confirm('답글작성을 취소하시겠습니까?')){
			location.href = `/board/boardList?category=${category}`;
	}
});