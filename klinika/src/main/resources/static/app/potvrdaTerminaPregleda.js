Vue.component("potvrda-termina-pregleda", {
	data : function() {
		return {
			token:"",
			pregled:{}
		} 
	},
	template: `
	<div>
		<div class="modal-dialog" role="document">
	    	<div class="modal-content">
				<div class="modal-header">
	        		<h5 class="modal-title" id="exampleModalLabel">Odgovor na termin za pregled</h5>
	      		</div>
	      		<div class="modal-body">
	        		<ul class="list-group">
					  	<li class="list-group-item">
					  		<div class="d-flex w-20 justify-content-between">
						  		<h6>Lekar:</h6>
						  		<p class="mb-0">{{ this.pregled.lekar.ime }} {{ this.pregled.lekar.prezime }}</p>
						  	</div>
					  	</li>
					  	<li class="list-group-item">
					  		<div class="d-flex w-20 justify-content-between">
						  		<h6>Vreme:</h6>
						  		<p class="mb-0">{{ formatVreme(this.pregled.vreme) }}</p>
						  	</div>
					  	</li>
					  	<li class="list-group-item">
					  		<div class="d-flex w-20 justify-content-between">
					  			<h6>Trajanje:</h6>
					  			<p class="mb-0">{{ formatTrajanje(this.pregled.trajanje) }}</p>
					  		</div>
					  	</li>
					  	<li class="list-group-item">
					  		<div class="d-flex w-20 justify-content-between">
						  		<h6>Adresa:</h6>
						  		<p class="mb-0">{{ this.pregled.sala.naziv }}</p>
						  	</div>
					  	</li>
					  	<li class="list-group-item">
					  		<div class="d-flex w-20 justify-content-between">
					  			<h6>Tip pregleda:</h6>
					  			<p class="mb-0">{{ this.pregled.tipPregleda.naziv }}</p>
					  		</div>
					  	</li>
					</ul>
	      		</div>
	      		<div class="modal-footer">
	      			<button class="btn btn-danger mr-auto" v-on:click="odgovor(false)">Odbij</button>
	  				<button class="btn btn-primary" v-on:click="odgovor(true)">Potvrdi</button>
	      		</div>
	    	</div>
		</div>
	</div>
	`,
	mounted: function(){
		this.token=this.$route.params.token;
		axios
		.get("auth/dobaviPodatkeoPregledu/"+this.token)
		.then(response=>{
			this.pregled=response.data;
		});
	},
	methods:{
		odgovor:function(odg){
			axios
			.post('/auth/proveriStanjePregleda', {token:this.token,odgovor:odg})
			.then(response=>{
				if (response.data == "odbijen") {
					alert("Ovaj pregled ste već odbili.");
				} else if (response.data == "potvrdjen") {
					alert("Ovaj pregled ste već potvrdili.");
				} else if (response.data == "ni jedno ni drugo") {
					axios
					.post('/auth/odgovorNaPotvrduTerminaPregleda',{token:this.token,odgovor:odg})
					.then(response=>{
						this.$router.push({ name: 'login' });
					});
				} else {
					alert("Greška!");
				}
			});
		},
		formatTrajanje: function(trajanje){
			return parseInt(trajanje)/60000+ " minuta"
		},
		formatVreme:function(datum){
            var date = new Date(datum);
            datum = date.toLocaleDateString('en-GB', {
            day: 'numeric', month: 'short', year: 'numeric'
            }).replace(/ /g, '-');
            datum=datum+" "+date.toLocaleTimeString()
            return datum
        },
	}
});