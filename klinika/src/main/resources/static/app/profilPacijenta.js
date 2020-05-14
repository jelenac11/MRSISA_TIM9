Vue.component("profil-pacijenta", {
	data : function() {
		return {
			token: "",
			korisnik:{},
			pacijent:{},
			nijeObavioNikadPregled:false,
			dijalog:false,
			zdravstveniKarton:{}
		} 
	},
	template: 
	`
	<v-app>
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 70rem;">
			<h4 class="card-header">Profil pacijenta</h4>
			<div class="card-body">
				
					<div class="form-row">
						<div class="col">
							<h6>Email adresa:</h6>
							<input type="text" v-model="pacijent.email" class="form-control" disabled>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    	 	<h6>Ime:</h6>
							<input type="text" v-model="pacijent.ime" class="form-control" disabled>
						</div>
				    	<div class="col">
				    		<h6>Prezime:</h6>
							<input type="text" v-model="pacijent.prezime" class="form-control" disabled>
				    	</div>
				    </div>
				    <div class="form-row">
				    	<div class="col">
				    		<h6>Adresa:</h6>
							<input type="text" v-model="pacijent.adresa" class="form-control" disabled>
				    	</div>
				    	<div class="col">
				    		<h6>Grad:</h6>
							<input type="text" v-model="pacijent.grad" class="form-control" disabled>
				    	</div>
				    	<div class="col">
				    		<h6>Država:</h6>
							<input type="text" v-model="pacijent.drzava" class="form-control" disabled>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<h6>Broj telefona:</h6>
							<input type="text" v-model="pacijent.brojTelefona" class="form-control" disabled>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
							<h6>Jedinstveni broj osiguranika - JBO</h6>
							<input type="text" v-model="pacijent.jbo" class="form-control" disabled>
				    	</div>
				  	</div>
				  	<div>
				  		<button class="btn btn-lg btn-primary" v-on:click="">Započni pregled</button>
						<button class="btn btn-lg btn-info" @click="dijalog = true" v-on:click="dobaviZdravstveniKarton" :disabled="!nijeObavioNikadPregled">Zdravstveni karton</button>
						<button class="btn btn-lg btn-secondary" style="float: right;" v-on:click="nazad">Nazad</button>
				  	</div>
			</div>
		</div>
		
		
		<v-dialog v-model="dijalog">
		<v-card>
			<div class="naviga">
				<div class="container d-flex justify-content-center">
					<div class="card mt-5" style="width: 70rem;">
						<h4 class="card-header">Zdravstveni karton</h4>
						<div class="card-body">
							<ul class="list-group">
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
								  		<h6>Ime:</h6>
								  		<p class="mb-0">{{ this.pacijent.ime }}</p>
								  	</div>
								  	<div class="d-flex w-20 justify-content-between">
								  		<h6>Prezime:</h6>
								  		<p class="mb-0">{{ this.pacijent.prezime }}</p>
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
		<v-card>		
		</v-dialog>
	</div>	
	</v-app>
	`,
	methods:{
		nazad:function(){
			this.$router.replace({ name: 'pacijenti' });
		},
		dobaviZdravstveniKarton: function(){
			axios
    		.put('/zdravstveniKartoni/dobaviKartonPacijenta', this.pacijent, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { 
            	this.zdravstveniKarton = response.data;
            })
            .catch(function (error) { console.log(error); });
		}
	
	},
	
	mounted(){
		this.token = localStorage.getItem("token");
		this.pacijent = JSON.parse(localStorage.getItem("pacijent"));
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.korisnik=response.data;
			axios
			.post('pregledi/provjeraPregledaZaPacijentaOdLekara',{idLekara:this.korisnik.id,idPacijenta:this.pacijent.id},{ headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{
				if(response.data==true){
					this.nijeObavioNikadPregled=true;
				}
				else
				{
					this.nijeObavioNikadPregled=false;
				}
			})
			.catch(function (error) { console.log(error); });
        })
		.catch(function (error) { console.log(error); });
	}
});