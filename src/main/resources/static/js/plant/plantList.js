'use strict';

const $searchBtn 	= document.getElementById('searchBtn');
let keyword 		= document.getElementById('keyword').value;

//검색 버튼 클릭시
$searchBtn.addEventListener('click', e=>{
	
	//검색어입력유무
	if($keyword.value.trim().length == 0){
		alert('검색어를 입력하세요');
		$keyword.focus();
		$keyword.select();
		return false;
	}
	  $.ajax({
	  url :  "/plant/list?keyword={keyword}",
	  method : "GET",
	  data : keyword,
	  })
	  .done(function (fragment) {
        $('#plantList').replaceWith(fragment);
    });

});






var selTag = ["","","","","",""];
var flag = [0, 0, 0, 0, 0, 0]; //카테고리1 선택여부
var btn = document.querySelectorAll('.tagBtn1');
var btnDiv1 = document.querySelectorAll('#tag1');
var btnDiv2 = document.querySelectorAll('#tag2');
// btnDiv2.style.display=='none';
for (let i = 0; i < btn.length; i++) {
	btn[i].addEventListener('click', function() {
		selTag1(i);
	});
}

for (let i = 0; i < btnDiv2.length; i++) {
	btnDiv2[i].addEventListener('click', function() {
		selTag2(i);
	});
}

//tag1 클릭
function selTag1(i) {
	let target;
	if (flag[i] == 0) {  //1번 클릭
		btnDiv2[i].style.display = 'block'
		btnDiv2[i].prepend(btn[i]);
		flag[i] = 1;
	} else if (flag[i] == 1) { //2번 클릭
		var btn2 = btnDiv2[i].querySelectorAll(".tagBtn2");
		btn2.forEach(function(e) {
			e.classList.remove('selectTag');
		});
		selTag[i] = '';
		selTags(selTag);
		// location.href = `/plant/list/${selTag}`;
		btnDiv1[i].append(btn[i]);
		btnDiv2[i].style.display = 'none'
		flag[i] = 0;   //원래 자리로 이동
	}
}

//tag2 클릭
function selTag2(i) {
	var btn2 = btnDiv2[i].querySelectorAll(".tagBtn2");
	for (let j = 0; j < btn2.length; j++) {
		btn2[j].addEventListener('click', function() {
			btn2.forEach(function(e) {
				e.classList.remove('selectTag');
			});
			this.classList.add('selectTag');
			selTag[i] = btn2[j].textContent;
			selTags(selTag);
			console.log(selTag);

		});
	}

}

//태그 클릭시 검색결과 반영
function selTags(selTag){
  var PlantTagDTO  = { 
    "PLIGHT" :selTag[0] ,
    "PMANAGELV":selTag[1],
    "PWATERSP":selTag[2],
    "PGROWTH" :selTag[3],
    "PFLCOLOR":selTag[4],
    "PGRWHSTLE" :selTag[5]
  }


	 $.ajax({
	  url :  "/plant/list/tag",
	  method : "GET",
	  data : PlantTagDTO,
	  })
	  .done(function (fragment) {
        $('#plant_view').replaceWith(fragment);
    });
}

