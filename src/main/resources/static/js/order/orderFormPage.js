/**
 * 
 */
 
 const bankBtn = document.getElementById('bank');
 const cardBtn = document.getElementById('card');
 const bank = document.querySelector('.bank');
 const card = document.querySelector('.card');
 
 bankBtn.addEventListener('click', e=>{
	bank.classList.remove('hidden');
	card.classList.add('hidden');
})

	cardBtn.addEventListener('click', e=>{
		card.classList.remove('hidden');
		bank.classList.add('hidden');
	});
	
	const $memberInfo = document.getElementById('memberInfo');
	const $newInfo = document.getElementById('newInfo');
	
	$memberInfo.addEventListener("click", e => {
		document.getElementById('d-name').value = 
				document.getElementById('hiddenName').value;
				
		document.getElementById('sample4_detailAddress').value =
				document.getElementById('hiddenAddress').value;
		
		document.getElementById('d-tel').value = 
				document.getElementById('hiddenTel').value;
		document.getElementById('sample4_postcode').value = 
				document.getElementById('hiddenPost').value;
		document.getElementById('sample4_roadAddress').value = 
				document.getElementById('hiddenRoadName').value;
	});
	
	$newInfo.addEventListener("click", e=>{
		document.getElementById('d-name').value = '';
		document.getElementById('sample4_detailAddress').value = '';
		document.getElementById('d-tel').value = '';
		document.getElementById('sample4_postcode').value = '';
		document.getElementById('sample4_roadAddress').value = '';
	})
	
	const $sumLists = document.querySelectorAll('#sum');
	let sum = 0;
	for( const product of $sumLists){
		sum = sum + parseInt(product.value);
		}
	document.getElementById('totalSum').value = sum;
	
	
	
	
	
	
	