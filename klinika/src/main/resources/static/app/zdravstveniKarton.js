Vue.component("zdravstveni-karton", {
	data : function() {
		return {
			ulogovan: {},
			zdravstveniKarton: {},
			token: "",
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		<div class="container d-flex justify-content-center">
			<div class="card mt-5" style="width: 70rem;">
				<h4 class="card-header">Zdravstveni karton</h4>
				<div class="card-body">
					<ul class="list-group">
					  	<li class="list-group-item">
					  		<div class="d-flex w-20 justify-content-between">
						  		<h6>Ime:</h6>
						  		<p class="mb-0">{{ this.ulogovan.ime }}</p>
						  	</div>
						  	<div class="d-flex w-20 justify-content-between">
						  		<h6>Prezime:</h6>
						  		<p class="mb-0">{{ this.ulogovan.prezime }}</p>
						  	</div>
					  	</li>
					  	<li class="list-group-item">
					  		<div class="d-flex w-20 justify-content-between">
						  		<h6>Visina:</h6>
						  		<p class="mb-0">{{ this.zdravstveniKarton.visina }}</p>
						  	</div>
						  	<div class="d-flex w-20 justify-content-between">
						  		<h6>Tezina:</h6>
						  		<p class="mb-0">{{ this.zdravstveniKarton.tezina }}</p>
						  	</div>
					  	</li>
					  	<li class="list-group-item">
					  		<div class="d-flex w-20 justify-content-between">
					  			<h6>Krvna grupa:</h6>
					  			<p class="mb-0">{{ this.zdravstveniKarton.krvnaGrupa }}</p>
					  		</div>
					  		<div class="d-flex w-20 justify-content-between">
					  			<h6>Dioptrija:</h6>
					  			<p class="mb-0">{{ this.zdravstveniKarton.dioptrija }}</p>
					  		</div>
					  	</li>
					  	<li class="list-group-item">
					  		<div class="d-flex w-20 justify-content-between">
					    		<h6>Izvestaji:</h6>
					    		<ul class="list-group">
									<li v-for="izvestaj in this.zdravstveniKarton.bolesti" class="list-group-item">{{ bolest }}</li>
								</ul>
							</div>
					  	</li>
					</ul>
				</div>
			</div>
		</div>	
	</div>
	</div>
	`
	,
	created() {
		this.token = localStorage.getItem("token");
	},
	mounted() {
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.ulogovan = response.data;
        	axios
    		.put('/zdravstveniKartoni/dobaviKartonPacijenta', this.ulogovan, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { 
            	this.zdravstveniKarton = response.data;
            })
            .catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
	        
	}
});