Vue.component("zahtevi-registracija",{
	data : function(){
		return {
            zahtevi : null,
            indeks: null,
            ulogovan: {},
            submitovano: false,
            token: "",
            dijalog: false,
            dijalogGreska: false,
		}
	},
	
    template: `
	<div>
    	<navig-bar v-bind:token="this.token"></navig-bar>
    	<v-app>
		    
	        <table class="naviga table table-hover table-striped">
	            <thead class="thead-light">
	                <tr>
	                    <th scope="col">Email</th>
	                    <th scope="col">Ime</th>
	                    <th scope="col">Prezime</th>
	                    <th scope="col">Adresa</th>
	                    <th scope="col">Grad</th>
	                    <th scope="col">Drzava</th>
	                    <th scope="col">JBO</th>
	                    <th scope="col" colspan="2">Akcija</th>
	                </tr>
	            </thead>
	            <tbody>
	                <tr v-for="(zahtev, indeks) in zahtevi">
	                    <td>{{zahtev.email}}</td>
	                    <td>{{zahtev.ime}}</td>
	                    <td>{{zahtev.prezime}}</td>
	                    <td>{{zahtev.adresa}}</td>
	                    <td>{{zahtev.grad}}</td>
	                    <td>{{zahtev.drzava}}</td>
	                    <td>{{zahtev.jbo}}</td>
	                    <td><button type="button" style="color:white;" class="btn btn-success" v-on:click="prihvatanjeZahteva(indeks)">Prihvati zahtev</button></td>
	                    <td><button type="button" style="color:white;" class="btn btn-danger" @click="dijalog = true" v-on:click="zabeleziIndeks(indeks)">Odbij zahtev</button></td>
	                </tr>
	            </tbody>
	        </table>
			
			<v-dialog v-model="dijalog" max-width="400px">
				<v-card>
					<div class="naviga">
						<form class="needs-validation mb-4"  id="forma-obrazlozenje-odbijanja">
				    	<div class="modal-content">
							<div class="modal-header">
				        		<h5 class="modal-title" id="exampleModalLabel">Obrazlozenje odbijanja zahteva</h5>
				      		</div>
				      		<div class="modal-body">
				              	<input id="obrazlozenje" type="text" class="form-control" placeholder="Unesite obrazloženje odbijanja zahteva" required>
				      			<div class="invalid-feedback" id="dodavanjeInvalid">Unesite razlog odbijanja zahteva za registracijom.</div>
				      		</div>
				      		<div class="modal-footer">
				        		<button type="button" style="color:white;" class="btn btn-secondary mr-auto" @click="dijalog = false">Nazad</button>
				        		<button class="btn btn-primary" v-on:click="odbijanjeZahteva">Odbij zahtev</button>
				      		</div>
				    	</div>
				    </form>
					</div>	
				</v-card>		
			</v-dialog>
			<v-dialog v-model="dijalogGreska" max-width="300">
			      <v-card>
			        <v-card-title class="headline">Greška</v-card-title>
			        <v-card-text>Neko drugi je upravo odgovorio na taj zahtjev.</v-card-text>
			        <v-card-actions>
			          <v-spacer></v-spacer>
			          <v-btn color="green darken-1" text @click="dijalogGreska = false">u redu</v-btn>
			        </v-card-actions>
			      </v-card>
			</v-dialog>
		</v-app>
    </div>
	`
		,
	methods: 
	{
		prihvatanjeZahteva: function(indeks) {
			this.zahtevi[indeks].aktiviran = true;
            this.zahtevi[indeks].obrazlozenje = "";
            this.updateZahtev(indeks);
        },
        zabeleziIndeks: function(indeks) {
            this.indeks = indeks;
            $("#obrazlozenje").val("");
            this.submitovano = false;
        },
		odbijanjeZahteva: function() {
			this.submitovano = true;
			if (document.getElementById('forma-obrazlozenje-odbijanja').checkValidity() === true){
				this.zahtevi[this.indeks].aktiviran = false;
	            this.zahtevi[this.indeks].obrazlozenje = $("#obrazlozenje").val();
	            jQuery.noConflict();
	            this.dijalog = false;
	            this.updateZahtev(this.indeks);
	            this.submitovano = false;
			}
        },
        updateZahtev: function(indeks) {
            axios
            .put('/auth/updateRegZahtev', this.zahtevi[indeks], { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { 
            	if(response.data){
            		toast("Uspesno ste odgovorili na zahtev.");
            	}
            	else{
            		this.dijalogGreska = true;
            	}
            	this.dobaviZahteve();
            	
            })
            .catch(function (error) { console.log(error); });
        },
        dobaviZahteve: function() {
            axios
            .get('/pacijenti/vratiRegZahteve', { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { this.zahtevi = response.data; })
            .catch(function (error) { console.log(error); });
        },
	},
	created () {
		this.token = localStorage.getItem("token");
        this.dobaviZahteve();
	}
});