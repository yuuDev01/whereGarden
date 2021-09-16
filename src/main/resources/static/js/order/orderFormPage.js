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
				
		document.getElementById('d-address').value =
				document.getElementById('hiddenAddress').value;
		
		document.getElementById('d-tel').value = 
				document.getElementById('hiddenTel').value;
		document.getElementById('d-post').value = 
				document.getElementById('hiddenPost').value;
		document.getElementById('d-roadname').value = 
				document.getElementById('hiddenRoadName').value;
	});
	
	$newInfo.addEventListener("click", e=>{
		document.getElementById('d-name').value = '';
		document.getElementById('d-address').value = '';
		document.getElementById('d-tel').value = '';
		document.getElementById('d-post').value = '';
		document.getElementById('d-roadname').value = '';
	})