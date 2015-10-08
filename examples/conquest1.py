# Basic algorithm test
#
# Poker's algorithm:
# Everybody plays the same value of ownership and it's distributed 
# accordingly with the number of players per team, linearly.

import matplotlib.pyplot as plt
import numpy

def game(teams):
	n_players = sum([ team["n_players"] for team in teams])
	LOSS = n_players #some constant that depends on the number of players

	team_count = {}

	points_to_dist = 0
	totalAmountPerPlayer = 0
	for team in teams:
		if team["ownership"] >= LOSS:
			points_to_dist += LOSS
			team["ownership"] -= LOSS
			totalAmountPerPlayer += team["n_players"]
		else:
			points_to_dist += team["ownership"]
			totalAmountPerPlayer += (team["ownership"] / LOSS) * team["n_players"]
			team["ownership"] = 0

	cur_dist = []
	for team in teams:
		team["ownership"] += points_to_dist * (team["n_players"] / n_players);
		cur_dist.append(team["ownership"])

	print(cur_dist)

	return cur_dist
	

teams = [	{"id": 0, "n_players": 5, "ownership": 70.0},
			{"id": 1, "n_players": 5, "ownership": 20.0},
			{"id": 2, "n_players": 5, "ownership": 10.0}
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

