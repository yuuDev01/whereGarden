'use strict';

const $modifyBtn 	= document.getElementById('modifyBtn');  		
const $delBtn 		= document.getElementById('deleteBtn');
const $replyBtn 	= document.getElementById('replyBtn');		
const $listBtn 		= document.getElementById('listBtn');

const $commentDelBtn 		= document.querySelectorAll('commentDelBtn');

const handler = (res, e) => {
	//console.log(e);
	if(res.rtcd == '00'){
		const category = e.target.dataset.category;
		location.href = `/board/boardList?category=${category}`;
	}else{
		//alert('삭제오류!');
		return false;
	}
}

//수정
$modifyBtn?.addEventListener("click", e=>{
	const bnum = e.target.dataset.bnum;
	location.href = `/board/boardModify/${bnum}`;
});

//삭제
$delBtn?.addEventListener("click", e=>{
	const bnum = e.target.dataset.bnum;
	const url = `/board/${bnum}`;
	
	if(confirm('삭제하시겠습니까?')){
		request.delete(url)
					 .then(res=>res.json())
					 .then(res=>handler(res, e))
					 .catch(err=>console.log(err));					 
	}
});

//답글
$replyBtn?.addEventListener("click",e=>{
		const bnum = e.target.dataset.bnum;
	location.href=`/board/replyQnA/${bnum}`;	
});

//목록
$listBtn?.addEventListener("click", e=>{
	const category = e.target.dataset.category;
	if(confirm('목록으로 돌아가시겠습니까?')){
		location.href = `/board/boardList?category=${category}`;
		}	
});

//댓글삭제
$commentDelBtn?.addEventListener("click", e=>{
	const cnum = e.target.dataset.cnum;
	if(confirm('댓글을 삭제 하시겠습니까?')){
		location.href = `/board/comment/del/${cnum}`;
		}	
});



