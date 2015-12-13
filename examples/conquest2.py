# More advanced algorithm test
#
# Graph's algorithm:
# Every team sends ownership to all the teams that have more or equal 
# number of players (neighbours). The amount sent by each team depends 
# on the current ownership and the number of players in the neighbours

import matplotlib.pyplot as plt
import numpy

def game(teams):

	n_players = sum([ team["n_players"] for team in teams])
	C = 0.01
	
	for team in teams:
		player_count = 0
		n_teams = 0
		for other in teams:
			if other["id"] != team["id"] and other["n_players"] >= team["n_players"]:
				player_count += other["n_players"]
				n_teams += 1

		loss = (player_count / team["n_players"]) * C * team["ownership"]
		team["gift"] = loss
		team["ownership"] -= loss * n_teams


	cur_dist = []
	for team in teams:
		for other in teams:
			if other["id"] != team["id"] and other["n_players"] <= team["n_players"]:
				team["ownership"] += other["gift"]

		cur_dist.append(team["ownership"])

	print(cur_dist)

	return cur_dist
	

teams = [	{"id": 0, "n_players": 5, "ownership": 40.0},
			{"id": 1, "n_players": 5, "ownership": 20.0},
			{"id": 2, "n_players": 5, "ownership": 15.0},
			{"id": 3, "n_players": 5, "ownership": 12.0},
			{"id": 4, "n_players": 5, "ownership": 5.0},
			{"id": 5, "n_players": 5, "ownership": 8.0}
		]

plots = [[] for _ in range(len(teams))]

i = 0
while i < 100:
	dist = game(teams)
	i+= 1

	for j in range(len(dist)):
		plots[j].append(dist[j])


xscale = range(len(plots[0]))
fig, ax = plt.subplots()

for l in plots:
	ax.plot(xscale, l, c=numpy.random.rand(3,))

plt.ylabel('conquest overtime')
plt.show()

