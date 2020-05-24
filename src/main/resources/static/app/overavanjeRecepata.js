Vue.component("overavanje-recepata",{
	data : function(){
		return {
            recepti : [],
            ulogovan: {},
            token: "",
		}
	},
	
    template: `
	<div>
	    <navig-bar v-bind:token="this.token"></navig-bar>
	    
        <table class="naviga table table-hover table-striped">
            <thead class="thead-light">
                <tr>
                    <th scope="col" width="20%">Pacijent</th>
                    <th scope="col" width="20%">Lekar</th>
                    <th scope="col" width="20%">Vreme</th>
                    <th scope="col" width="15%">Šifra</th>
                    <th scope="col" width="15%">Naziv leka</th>
                    <th scope="col" width="10%"></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="recept in recepti">
                    <td width="20%">{{ recept.pacijent }}</td>
                    <td width="20%">{{ recept.lekar }}</td>
                    <td width="20%">{{ urediDatum(recept.datum) }}</td>
                    <td width="15%">{{ recept.lek.sifra }}</td>
                    <td width="15%">{{ recept.lek.naziv }}</td>
                    <td width="10%"><button type="button" class="btn btn-success" v-on:click="overiRecept(recept)">Overi</button></td>
                </tr>
            </tbody>
        </table>
    </div>
	`
		,
	methods: 
	{
		urediDatum: function(datum) {
	        var date = new Date(datum);
	        datum = date.toLocaleDateString('en-GB', {
	        day: 'numeric', month: 'short', year: 'numeric'
	        }).replace(/ /g, '-');
	        vreme = date.toLocaleTimeString();
	        return datum + " " + vreme
		},
		overiRecept: function(recept) {
            axios
            .post('/recepti/overi', recept, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { 
            	this.dobaviRecepte(); 
            })
            .catch(function (error) { console.log(error); });
        },
        dobaviRecepte: function() {
            axios
            .get('/recepti/ucitajNeoverene', { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { this.recepti = response.data; })
            .catch(function (error) { console.log(error); });
        },
	},
	created () {
		this.token = localStorage.getItem("token");
        this.dobaviRecepte();
	}
});