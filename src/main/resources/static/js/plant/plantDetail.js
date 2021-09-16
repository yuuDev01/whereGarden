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