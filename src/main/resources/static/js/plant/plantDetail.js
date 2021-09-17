'use strict';

  function fnCalCount(type){
    var input = document.getElementById('cnt');
    var count = input.value;
    console.log(count);
    if(type=='p'){
        input.value = Number(count)+1;
    }else{
      if(Number(count) == 0) {
      	input.value = 0;
      }else{
      	input.value = Number(count)-1;
    }
 }
}

const $cartBtn = document.getElementById('cartBtn');
console.log("클릭");
$cartBtn.addEventListener('click', e => {
	if(confirm('상품이 담겼습니다. 장바구니로 이동하시겠습니까?')){
		document.getElementById('cartFlag').value = 1;
	}
});