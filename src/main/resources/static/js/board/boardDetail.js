'use strict';

const $modifyBtn 	= document.getElementById('modifyBtn');  		
const $delBtn 		= document.getElementById('deleteBtn');
const $replyBtn 	= document.getElementById('replyBtn');		
const $listBtn 		= document.getElementById('listBtn');

const $commentDelBtn 		= document.getElementById('commentDelBtn');
const $commentMoiBtn 		= document.getElementById('commentMoiBtn');

const handler = (res, e) => {
	//console.log(e);
	if(res.rtcd == '00'){
		const category = e.target.dataset.category;
		location.href = `/board/boardList?category=${category}`;
	}else{
		alert('댓글이 있어 삭제가 불가능합니다');
		return false;
	}
}

//게시글 수정
$modifyBtn?.addEventListener("click", e=>{
	const bnum = e.target.dataset.bnum;
	location.href = `/board/boardModify/${bnum}`;
});

//게시글 삭제
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

//댓글수정
$commentMoiBtn?.addEventListener("click", e=>{
	/*부모의 다음 형제의 자식*/
	const bnum = e.target.dataset.bnum;	//th-data로 글번호 받아옴
	
	console.log($commentMoiBtn.parentNode.nextElementSibling.firstElementChild);		//댓글내용창
	$commentMoiBtn.parentNode.nextElementSibling.firstElementChild.readOnly=false;	// readonly삭제
	$commentMoiBtn.parentNode.nextElementSibling.firstElementChild.style.border="solid";
	$commentMoiBtn.nextElementSibling.remove(); //삭제버튼 제거
	$commentMoiBtn.innerHTML="수정완료";
	var cancelButton = document.createElement("button");
	cancelButton.className = 'btn btn-success';
	cancelButton.id = 'commentModiCancel';
	cancelButton.innerHTML = "취소";
	$commentMoiBtn.parentNode.appendChild(cancelButton);
	
	cancelButton.addEventListener("click", e=>{
		location.href = `/board/${bnum}`;
		});
		
		$commentMoiBtn.addEventListener("click", e=>{
			const $commentTextarea = document.getElementById('commentTextarea');
			$commentTextarea.style.border="0";
			$commentTextarea.readOnly=true;
			$commentMoiBtn.innerHTML="수정하기";
		/*location.href = `/`;*/
		});
});





