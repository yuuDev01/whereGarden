/**
 * 
 */

 //전체 체크박스 체크
 function selectAll(selectAll) {
      const checkboxes = document.querySelectorAll('input[type="checkbox"]');
      checkboxes.forEach((checkbox)=>{
           checkbox.checked = selectAll.checked
      })
  }

//장바구니 선택상품 주문
const $orderBtn = document.getElementById('orderBtn');
$orderBtn.addEventListener('click', e => {
   const checkedProduct = document.querySelectorAll('input[type="checkbox"]:not(:checked)');
//   $checkedProduct.forEach(ele => {
//      console.log(ele.nextSibling);
//      console.log(ele.nextSibling.nextSibling);
//      console.log(ele.nextSibling.nextSibling.nextSibling);
//      console.log(ele.nextSibling.nextSibling.nextSibling.nextSibling);
//   })
   const nullCheckBoxValue = document.getElementById('cartChk').value;
   for(const unChecked of checkedProduct){
      if(unChecked.value == nullCheckBoxValue) continue;
     	console.log(unChecked.nextElementSibling);
     	console.log(unChecked.nextElementSibling.nextElementSibling);
     	console.log(unChecked.nextElementSibling.nextElementSibling.nextElementSibling);
     	console.log(unChecked.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling);
      unChecked.previousElementSibling.disabled = true;
      unChecked.nextElementSibling.disabled = true;
      unChecked.nextElementSibling.nextElementSibling.disabled = true;
      unChecked.nextElementSibling.nextElementSibling.nextElementSibling.disabled = true;
      unChecked.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.disabled = true;
      //총 합계 disabled
      document.getElementById('allSum').disabled = true;
   }
});

//장바구니 전체상품 주문
const $allBtn = document.getElementById('allBtn');
$allBtn.addEventListener('click', e=>{
	const checkedProduct = document.querySelectorAll('input[type="checkbox"]:not(:checked)');
	const checkBox = document.querySelectorAll('input[type="checkbox"]');
	
	 var notChecked = "";
	 for(var i=0; i<checkBox.length; i++) {
		if(checkBox[i].checked == true){
			notChecked = "1";
		}
	}
	const $plantName = document.getElementById('plantName');
	if(notChecked == "" && $plantName == null){
		alert("장바구니가 비었습니다");
		return false;
	} else{
		for( const unChecked of checkedProduct) {
			unChecked.checked = true;
			document.orderForm.submit();
		}
	}	
});
   //장바구니 수량 및 합계 변경
       const cartNums = document.querySelectorAll(".cartNum");
       for(const cartNum of cartNums){
          cartNum.addEventListener('change', e=>{
             const url = `/cart/edit`;
            const {idx,cmid,cpid,cnum,cprice} = e.target.dataset;
            const cqty = e.target.value;
            const $sumBoxes = document.querySelectorAll('#sum');
            console.log(parseInt(cprice)*parseInt(cqty))
            $sumBoxes[idx].value = parseInt(cprice)*parseInt(cqty);

            // 총 합계 계산
            const sums = document.querySelectorAll('#sum');
            var allSum = 0;
            for(const sum of sums){
               allSum = parseInt(allSum) + parseInt(sum.value);
            }
            document.getElementById('allSum').value = allSum;
            
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
   
   //장바구니 총 금액합계
   const sums = document.querySelectorAll('#sum');
   var allSum = 0;
   for(const sum of sums){
      allSum = parseInt(allSum) + parseInt(sum.value);
   }
   document.getElementById('allSum').value = allSum;
   
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
   