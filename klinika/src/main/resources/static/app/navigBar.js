Vue.component('navig-bar', {
	data : function() {
		return {
			ulogovan: {},
			uloga: "",
		} 
	},
	props: ['token'],
	name: 'navig-bar',
	template: `
		<div>
			<nav class="navbar navbar-expand-md navbar-dark bg-dark">
			    <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
			        <ul class="nav nav-pills" role="tablist">

			        	<li v-if="this.uloga == 'ROLE_PACIJENT'" class="nav-item">
							<router-link :to="{ name: 'pretragaKlinika', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Klinike</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_PACIJENT'" class="nav-item">
							<router-link :to="{ name: 'istorijaPregleda', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Istorija pregleda</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_PACIJENT'" class="nav-item">
							<router-link :to="{ name: 'zdravstveniKarton', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Zdravstveni karton</router-link>
						</li>
						
						<li v-if="this.uloga == 'ROLE_LEKAR'" class="nav-item">
							<router-link :to="{ name: 'pacijenti', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Pacijenti</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_LEKAR'" class="nav-item">
							<router-link :to="{ name: 'zapocniPregled', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Započni pregled</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_LEKAR'" class="nav-item">
							<router-link :to="{ name: 'radniKalendar', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Radni kalendar</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_LEKAR'" class="nav-item">
							<router-link :to="{ name: 'zahtevOdsustvo', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Odsustvo</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_LEKAR'" class="nav-item">
							<router-link :to="{ name: 'zakazivanjeLekar', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Zakaži pregled</router-link>
						</li>
						
						<li v-if="this.uloga == 'ROLE_MED_SESTRA'" class="nav-item">
							<router-link :to="{ name: 'pacijenti', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Pacijenti</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_MED_SESTRA'" class="nav-item">
							<router-link :to="{ name: 'radniKalendar', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Radni kalendar</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_MED_SESTRA'" class="nav-item">
							<router-link :to="{ name: 'zahtevOdsustvo', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Odsustvo</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_MED_SESTRA'" class="nav-item">
							<router-link :to="{ name: 'overavanjeRecepata', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Overi recepte</router-link>
						</li>
						
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINICKOG_CENTRA'" class="nav-item">
							<router-link :to="{ name: 'zahteviRegistracija', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Zahtevi za registraciju</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINICKOG_CENTRA'" class="nav-item">
							<router-link :to="{ name: 'sifrarnici', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Šifrarnici</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINICKOG_CENTRA'" class="nav-item">
							<router-link :to="{ name: 'klinikeAdmin', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Klinike</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINICKOG_CENTRA'" class="nav-item">
							<router-link :to="{ name: 'adminiKlinike', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Admini klinike</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINICKOG_CENTRA'" class="nav-item">
							<router-link :to="{ name: 'adminiCentra', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Admini centra</router-link>
						</li>
						
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
							<router-link :to="{ name: 'klinika', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Klinika</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
							<router-link :to="{ name: 'termini', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Termini</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
							<router-link :to="{ name: 'poslovanje', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Poslovanje</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
							<router-link :to="{ name: 'sale', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Sale</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
							<router-link :to="{ name: 'tipoviPregleda', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Tipovi pregleda</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
							<router-link :to="{ name: 'lekari', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Lekari</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
							<router-link :to="{ name: 'medSestre', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Medicinske sestre</router-link>
						</li>
						<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
							<router-link :to="{ name: 'zahtjevi', params: { korisnikToken: this.token }}" data-toggle="pill" class="nav-link">Zahtevi odsustvo</router-link>
						</li>
						
					</ul>
			    </div>
		    
			    <div class="navbar-collapse collapse w-100 order-1 dual-collapse2">
			        <ul class="navbar-nav ml-auto">
			            <li class="nav-item dropdown">
			                <a class="navbar-brand dropdown-toggle" href="#" data-toggle="dropdown">
							    <img src="profilepic.png" width="30" height="30" class="d-inline-block align-top" alt="">
							    {{ this.ulogovan.ime + " " + this.ulogovan.prezime }}
						  	</a>
						  	<div class="dropdown-menu">
						  		<a class="dropdown-item" data-toggle="modal" data-target="#profilModal" href='#'>Pregled profila</a>
					        	<a class="dropdown-item" v-on:click="odjaviSe" href="#">Odjavi se</a>
					        </div>
			            </li>
			        </ul>
			    </div>
			</nav>
			
			<div class="modal fade" id="profilModal" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
			    	<div class="modal-content">
						<div class="modal-header">
			        		<h5 class="modal-title" id="exampleModalLabel">Pregled profila</h5>
			        		<button type="button" class="close" data-dismiss="modal">
			          			<span>&times;</span>
			        		</button>
			      		</div>
			      		<div class="modal-body">
			        		<ul class="list-group">
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
								  		<h6>Email adresa:</h6>
								  		<p class="mb-0">{{ this.ulogovan.email }}</p>
								  	</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
								  		<h6>Ime:</h6>
								  		<p class="mb-0">{{ this.ulogovan.ime }}</p>
								  	</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
							  			<h6>Prezime:</h6>
							  			<p class="mb-0">{{ this.ulogovan.prezime }}</p>
							  		</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
								  		<h6>Adresa:</h6>
								  		<p class="mb-0">{{ this.ulogovan.adresa }}</p>
								  	</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
							  			<h6>Grad:</h6>
							  			<p class="mb-0">{{ this.ulogovan.grad }}, {{this.ulogovan.drzava}}</p>
							  		</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
							  			<h6>Broj telefona:</h6>
							  			<p class="mb-0">{{ this.ulogovan.brojTelefona }}</p>
							  		</div>
							  	</li>
							</ul>
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
			        		<router-link :to="{ name: 'izmenaProfila', params: { korisnikToken: this.token } }" class="btn btn-primary" data-dismiss="modal">Izmena podataka</router-link>
			      		</div>
			    	</div>
				</div>
			</div>
		</div>
	`
	,
	methods : {
		odjaviSe : function () {
			this.$router.replace({ path: '/' });
		},
	},
	created() {
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.ulogovan = response.data;
        	axios
    		.put('/korisnici/dobaviUlogu', this.ulogovan, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { this.uloga = response.data; })
            .catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
	}
})