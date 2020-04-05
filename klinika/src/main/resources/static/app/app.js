/**
 * 
 */
const HelloWorld = { template: '<hello-world></hello-world>' }
const Base = { template: '<base></base>' }


const router = new VueRouter({
	 mode: 'hash',
	  routes: [
	    {
	    	path: '/api/greetings', 
	    	component: HelloWorld 
	    },
	    {
	    	path: '/', 
	    	component: Base 
	    }]
});

var app = new Vue({
	router,
	el: '#login'
});