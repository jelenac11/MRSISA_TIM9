Vue.component("definisanje-slobodnog-termina", {
	data : function() {
		return {
			currentTab: 0,
			token: "",
			terminPregleda:{
				idAdmina:0,
				datumiVreme:0,
				tipPregleda:null,
				trajanje:0,
				lekar:null,
				sala:null
			},
			tipoviPregleda:[],
			korisnik:{},
			sale:[],
			lekari:[],
			odabranDatum:true,
			odabraniTipPregleda:true,
			odabraniLekar:true,
			odabranaSala:true,
			odabranoTrajanje:true,
		} 
	},
	template: `
	<div>
		<navig-bar v-bind:token="this.token"></navig-bar>
			<main class="content" role="content">	
				<section id="section1">
					<div class="container d-flex justify-content-center">
						<div class="card mt-5" style="width: 47rem;">
							<form class="needs-validation mb-4" id="regForm">
							  <h4 class="card-header">Odabir datuma i tipa pregleda:</h4>
							  <div class="tab">
									<div class="form-row mb-3">
										<div class="col">
											<label for="datum" class="mt-1">Datum i vreme pregleda</label>
											<input type="datetime-local" v-model="terminPregleda.datumiVreme" class="form-control" id="datum" v-on:change="promjenaDatuma" v-bind:class="{ 'is-invalid':!odabranDatum}" required>
											<div class="invalid-feedback" id="dodavanjeInvalid">Odabrani datum je nevalidan.</div>
										</div>
									</div>
									<div class="form-row">
										<div class="col">
											<label for="trajanje" class="mt-1">Trajanje pregleda (u minutama)</label>
											<input type="number" min=1 max=120 v-model="terminPregleda.trajanje" class="form-control" id="datum" v-on:change="promjenaTrajanja" v-bind:class="{ 'is-invalid':!odabranoTrajanje}" required>
											<div class="invalid-feedback" id="dodavanjeInvalid">Pogresna vrednost.</div>
										</div>
									</div>
									<div class="form-row">
										<div class="col">
											<label for="tipPregleda" class="mt-1">Tip pregleda</label>
										    <select class="custom-select mt-0" v-model="terminPregleda.tipPregleda" id="tipPregleda" v-bind:class="{ 'is-invalid':!odabraniTipPregleda}" required>
										    	<option v-for="tip in tipoviPregleda" :value="tip">
													{{ tip.naziv }}
										    	</option>
									  		</select>
											<div class="invalid-feedback" id="dodavanjeInvalid">Niste odabrali tip pregleda.</div>
										</div>
									</div>
							  </div>
							  
							  <div class="tab">
									<div class="form-row">
										<div class="col">
											<label for="lekar" class="mt-1">Lekar</label>
										    <select class="custom-select mt-0" v-model="terminPregleda.lekar" v-bind:class="{ 'is-invalid':!odabraniLekar}" required>
										    	<option v-for="lekar in lekari" :value="lekar">
													{{ lekar.ime}} {{lekar.prezime }}
										    	</option>
									  		</select>
											<div class="invalid-feedback" id="dodavanjeInvalid">Niste odabrali lekara.</div>
										</div>
									</div>
									<div class="form-row">
										<div class="col">
											<label for="sala" class="mt-1">Sala</label>
										    <select class="custom-select mt-0" v-model="terminPregleda.sala" v-bind:class="{ 'is-invalid':!odabranaSala}" required>
										    	<option v-for="sala in sale" :value="sala">
													{{ sala.naziv }}
										    	</option>
									  		</select>
											<div class="invalid-feedback" id="dodavanjeInvalid">Niste odabrali salu.</div>
										</div>
									</div>
							  </div>
							  
							  <div style="overflow:auto;">
							    <div style="float:right;">
							      <button type="button" id="prevBtn" v-on:click="prev" class="btn btn-secondary  btn-lg">Previous</button>
							      <button type="button" id="nextBtn" v-on:click="next" class="btn btn-primary  btn-lg">Next</button>
							    </div>
							  </div>
							  <div style="text-align:center;margin-top:40px;">
							    <span class="step"></span>
							    <span class="step"></span>
							  </div>
							</form>
						</div>
					</div>
				</section>
			</main>
		</div>
	`,
	created: function(){
		this.token = localStorage.getItem("token");
	},
	mounted: function(){
		var css_text = `

		/* Hide all steps by default: */
			.tab {
			  display: none;
			}
			/* Step marker: Place in the form. */
			.step {
			  height: 15px;
			  width: 15px;
			  margin: 0 2px;
			  background-color: #bbbbbb;
			  border: none;  
			  border-radius: 50%;
			  display: inline-block;
			  opacity: 0.5;
			}
			
			.step.active {
			  opacity: 1;
			}
			
			/* Mark the steps that are finished and valid: */
			.step.finish {
			  background-color: #4CAF50;
			}
	        `;
		
	        var css = document.createElement('style');
	        css.type='text/css';
	        css.setAttributeNode( document.createAttribute('scopped') );
	        css.appendChild(    document.createTextNode( css_text )     );
	        
	        axios
			.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => { 
	        	this.korisnik=response.data;
	        	this.terminPregleda.idAdmina=this.korisnik.id;
	        	axios
	            .get('/tipoviPregleda/ucitajSvePoIdKlinike/'+this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
	            .then(response => (this.tipoviPregleda = response.data))
	            .catch(function (error) { console.log(error); });
	        })
	        .catch(function (error) { console.log(error); });
	       
	        
	        this.$el.appendChild(   css     );
		this.showTab(this.currentTab);
	},
	methods: {
		showTab: function(n){
			
			 var x = document.getElementsByClassName("tab");
			 if(n>=x.length){
				 return;
			 }
			  x[n].style.display = "block";
			  //... and fix the Previous/Next buttons:
			  if (n == 0) {
			    document.getElementById("prevBtn").style.display = "none";
			  } else {
			    document.getElementById("prevBtn").style.display = "inline";
			  }
			  if (n == (x.length - 1)) {
			    document.getElementById("nextBtn").innerHTML = "Submit";
			  } else {
			    document.getElementById("nextBtn").innerHTML = "Next";
			  }
			  //... and run a function that will display the correct step indicator:
			  this.fixStepIndicator(n)
		},
		next:function() {
			  // Exit the function if any field in the current tab is invalid:
			  if (this.currentTab==0 && !this.validateForm1()) return false;
			  if(this.currentTab==0){
				  this.dobaviPodatke();
				  return;
			  }
			  
			  if (this.currentTab==1 && !this.validateForm2()) return false;
			  
			  // This function will figure out which tab to display
			  var x = document.getElementsByClassName("tab");
			  // Hide the current tab:
			  x[this.currentTab].style.display = "none";
			  // Increase or decrease the current tab by 1:
			  this.currentTab = this.currentTab + 1;
			  // if you have reached the end of the form...
			  if (this.currentTab >= x.length) {
				  let termin=JSON.parse(JSON.stringify(this.terminPregleda))
				  termin.datumiVreme=Date.parse(termin.datumiVreme)
				  axios
				  .post("/pregledi/dodajSlobodanTerminZaPregled",termin, { headers: { Authorization: 'Bearer ' + this.token }})
				  .then(response=>{
					  if(response.data==true){
						  this.$router.replace({ name: 'lekari', params: { korisnikToken: this.token } });
					  }
					  else{
						  toast("Neuspesno kreiranje termina za pregled");
						  return;
					  }
				  })
				  .catch(error => {
						console.log(error);
						toast("Neuspesno kreiranje termina za pregled");
						this.uspesnoDodavanje = false;
					});
				  return;
			  }
			  // Otherwise, display the correct tab:
			  this.showTab(this.currentTab);
		},
		prev: function(){
			 // This function will figure out which tab to display
			  var x = document.getElementsByClassName("tab");
			 
			  // Hide the current tab:
			  x[this.currentTab].style.display = "none";
			  // Increase or decrease the current tab by 1:
			  this.currentTab = this.currentTab - 1;

			  // Otherwise, display the correct tab:
			  this.showTab(this.currentTab);
		},
		fixStepIndicator:function(n){
			 // This function removes the "active" class of all steps...
			  var i, x = document.getElementsByClassName("step");
			  for (i = 0; i < x.length; i++) {
			    x[i].className = x[i].className.replace(" active", "");
			  }
			  //... and adds the "active" class on the current step:
			  x[n].className += " active";
		},
		validateForm1:function(){
			
			this.promjenaTipaPregleda();
			this.promjenaDatuma();
			this.promjenaTrajanja();
			
			if(this.odabraniTipPregleda && this.odabranDatum && this.odabranoTrajanje){
				return true;
			}
			else{
				return false;
			}
			
		},
		validateForm2:function(){
			this.promjenaTipaPregleda();
			this.promjenaDatuma();
			
			if(this.terminPregleda.lekar==null){
				this.odabraniLekar=false;
			}
			else{
				this.odabraniLekar=true;
			}
			
			if(this.terminPregleda.sala==null){
				this.odabranaSala=false;
			}
			else{
				this.odabranaSala=true;
			}
			
			if(this.odabraniTipPregleda && this.odabranDatum && this.odabranaSala && this.odabraniLekar){
				return true;
			}
			else{
				return false;
			}	
		},
		promjenaTipaPregleda:function(){
			if(this.terminPregleda.tipPregleda==null){
				this.odabraniTipPregleda=false;
				return false;
			}
			else{
				this.odabraniTipPregleda=true;
				return true;
			}
		},
		promjenaDatuma:function(){
			if(this.terminPregleda.datumiVreme==0 || new Date(this.terminPregleda.datumiVreme).getTime()<=(new Date().getTime())){
				this.odabranDatum=false;
				return false;
			}
			else{
				
			this.odabranDatum=true;
			return true;
			}
		},
		promjenaTrajanja: function(){
			if(this.terminPregleda.trajanje>0 && this.terminPregleda.trajanje<=120){
				this.odabranoTrajanje=true;
				return true;
			}
			else{
				this.odabranoTrajanje=false;
				return false;
			}
		},
		dobaviPodatke: function(){
			let termin=JSON.parse(JSON.stringify(this.terminPregleda))
			termin.datumiVreme=Date.parse(termin.datumiVreme)
			axios
			.post("/lekari/dobaviSlobodneLekareZaPregled",termin, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{
				this.lekari=response.data;
				axios
				.post("/sale/dobaviSlobodneSaleZaPregled",termin, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response=>{
					this.sale=response.data;
					if(this.lekari.length==0 && this.sale.length==0){
						  toast("Nema slobodnih sala i lekara za izabrani termin.");
						  return false;
					  }
					  if(this.lekari.length==0){
						  toast("Nema slobodnih lekara za izabrani termin i tip pregleda.");
						  return false;
					  }
					  
					  if(this.sale.length==0){
						  toast("Nema slobodnih sala za izabrani termin.");
						  return false;
					  }
					  // This function will figure out which tab to display
					  var x = document.getElementsByClassName("tab");
					  // Hide the current tab:
					  x[this.currentTab].style.display = "none";
					  // Increase or decrease the current tab by 1:
					  this.currentTab = this.currentTab + 1;
					  // if you have reached the end of the form...
					  if (this.currentTab >= x.length) {
					    // ... the form gets submitted:
					    //ovdje cemo poslati na server
					    return false;
					  }
					  // Otherwise, display the correct tab:
					  this.showTab(this.currentTab);
					})
				.catch(function (error) { console.log(error); });
				})
			.catch(function (error) { console.log(error); });
		},
	}
});