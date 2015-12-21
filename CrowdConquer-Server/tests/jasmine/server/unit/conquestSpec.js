
describe('Conquest Module', function(){
	'use strict';

	it('should conquer one unprotected territory after 60 iterations, being attacked by an enemy team with one member.', function(){
		var teams = [{id: 0, nPlayers: 0, ownership: 100.0}, {id: 1, nPlayers: 1, ownership: 0}];
		for(var i=0; i<60; i++){
			Conquest.iterate(teams);
		}
		expect(teams[1].ownership).toBeGreaterThan(50);

	})
})