
var C = 0.01

/**
 * Every team sends ownership to all the teams that have more or equal number of players (neighbours).
 * The amount sent by each team depends on the current ownership and the number of players in the neighbours.
 *
 * @param	teams: array with teams in the area, with number of players, current ownership and id
 * @return	none
 */
function iterate(teams){

	var nPlayers = 0;
	for(var i=0; i<nPlayers; i++)
		nPlayers += team.nPlayers;

	for(var i=0; i<teams.length; i++){
		var playerCount = 0;
		var nTeams = 0;
		var team = teams[i];
		for(var o = 0; o<teams.length; o++){
			var other = teams[o]
			if(i == o || other.nPlayers < team.nPlayers)
				continue;
			
			playerCount += other.nPlayers;
			nTeams++;
			
		}
		console.log(i, playerCount);
		var div = team.nPlayers;
		if(div == 0)
			div = 0.2;

		var loss = (playerCount / div) * C * team.ownership;
		team.gift = loss;
		team.ownership -= loss * nTeams;
	}

	for(var i=0; i<teams.length; i++){
		var team = teams[i];
		for(var o=0; o<teams.length; o++){
			var other = teams[o];
			
			if(i == o || other.nPlayers < team.nPlayers)
				continue;

			team.ownership += other.gift;
		}
	}

}



Conquest = {};
Conquest.iterate = iterate;
