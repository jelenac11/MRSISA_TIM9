/**
 * 
 */

Vue.component("hello-world",{
	data : function(){
		return {
			msg : ""
		}
	},
	
	template: `
		<div> {{msg}} </div>
	`
		,
	mounted (){
		axios.get('/api/greetings').then(response => msg = response.data)
		.catch(function (error) { console.log(error); });
	}
});