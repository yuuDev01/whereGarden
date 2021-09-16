/**
 * 
 */
 //체크박스
 function selectAll(selectAll) {
		const checkboxes = document.querySelectorAll('input[type="checkbox"]');
		checkboxes.forEach((checkbox)=>{
  			checkbox.checked = selectAll.checked
		})
  }
  
	function getCheckBox() {
		const query = 'input[name="cartChk"]:checked';
		const selectedEls = document.querySelectorAll(query);
		
		selectedEls.forEach(ele => {
			if(ele.id = element.dataset.id) {
				
			}
		});
	}
	
	//장바구니 수량변경
	 	const cartNums = document.querySelectorAll(".cartNum");
	 	for(const cartNum of cartNums){
		 	cartNum.addEventListener('change', e=>{
		 		console.log('수량:',cartNum);
		 		const url = `/cart/edit`;
		 		const cmid = e.target.dataset.cmid;
		 		const cpid = e.target.dataset.cpid;
		 		const cnum = e.target.dataset.cnum;
		 		const cqty = e.target.value;
		 		const data = {
										    "cmid":cmid,
										    "cpid":cpid,
										    "cnum":cnum,
										    "cqty":cqty,
										 };
		 		
		 		request.patch(url,data)
		 					 .then(res => res.json())
		 					 .then(res => {
									if(res.rtcd == '00') {
										//성공로직
										console.log(res.data);
										e.target.value = res.data;
										//location.href=`/cart/myCartList`;
									} else {
										throw new Error(res.rtmsg);
									}
								}).catch(err=>{
									//오류로직
									errmsg.textContent = err.message;
								})
	 	});
	 	}
	//핸들러
	const handler = (res, e) => {
		console.log(e);
		if(res.rtcd == '00') {
			//const cnum = e.target.dataset.cnum;
			location.href=`/cart/myCartList`; //삭제가 제대로 됐으면 장바구니목록으로
		} else {
			alert('오류');
			return false;
		}
	}
	
	//장바구니 금액합계
	const $sum = document.querySelector('.sum');
	
	
	//장바구니 삭제
	const buttons = document.querySelectorAll(".delBtn");
	 	for (const button of buttons) {
	 	  button.addEventListener('click', e => {
	 	    const cnum = e.target.dataset.cnum;
	 	    //ajax 이용
	 	   	const url = `/cart/${cnum}`;
	 	   	
	 	   	if(confirm('장바구니에서 삭제하시겠습니까?')) {
					request.delete(url)
								 .then(res => res.json())
								 .then(res => handler(res,e))
								 .catch(err => console.log(err));
				}
	 	  });
	 	}
	