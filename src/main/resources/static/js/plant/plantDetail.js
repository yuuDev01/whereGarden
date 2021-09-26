'use strict';

	var favBtn = document.getElementById('heart');
	//console.log(pnum);

 // favBtn.onclick = function(){ changeHeart(); }
  
  favBtn.addEventListener('click',e=>{
	//console.log('click!');
	changeHeart();
})
 
 /* 좋아요 버튼 눌렀을떄 */
 function changeHeart(){ 
     $.ajax({
            type : "POST",  
            url : "/fav/"+pnum,       
            dataType : "text",   
      
        }).
        done(function (fragment) {
        $('#favorite').replaceWith(fragment);
 });
 }
 

const $cartBtn = document.getElementById('cartBtn');
console.log("클릭");
$cartBtn.addEventListener('click', e => {
	if(confirm('상품이 담겼습니다. 장바구니로 이동하시겠습니까?')){
		document.getElementById('cartFlag').value = 1;
	}
});