
cellIteration = function(teams){
	/*
		Poker's algorthm, by michel
	*/

	nPlayers = 0
	teams.forEach(function(el){ nPlayers += el.players; })
	LOSS = nPlayers // some constant that depends on the number of players
	team_count = {}

	pointsToDist = 0
	teams.forEach(function(el){
		if (el.ownership >= LOSS){
			pointsToDist += LOSS;
			el.ownership -= LOSS;
		}else{
			pointsToDist += el.ownership;
			el.ownership = 0;
			el.notPayied
		}

	});

	curDist = []
	teams.forEach(function(el){
		el.ownership += pointsToDist * (el.players / nPlayers);
		curDist.push(el.ownership);
	});

	return curDist;

}

teams = [
	{team_id: 'skdkay', players: 5, ownership: 100},
	{team_id: 'ewrmwe', players: 6, ownership: 0},
	{team_id: 'opsdca', players: 5, ownership: 0}

]


console.log(teams)

for(i=0; i<100; i++){
	l = cellIteration(teams)
	console.log(l);
}

/*def game(teams):
	n_players = sum([ team["n_players"] for team in teams])
	LOSS = n_players #some constant that depends on the number of players

	team_count = {}

	points_to_dist = 0
	for team in teams:
		if team["ownership"] >= LOSS:
			points_to_dist += LOSS
			team["ownership"] -= LOSS
		else:
			points_to_dist += team["ownership"]
			team["ownership"] = 0

	cur_dist = []
	for team in teams:
		team["ownership"] += points_to_dist * (team["n_players"] / n_players)
		cur_dist.append(team["ownership"])

	print(cur_dist)

	return cur_dist
*/
