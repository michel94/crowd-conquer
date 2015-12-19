
describe("Module", function(){
	'use strict';

	beforeEach(function() {
       TestCollection.remove({});
    });

	it("should add two non-complex objects", function(){
		var a = {a: 3, b: 4}
		var b = {c: 1, b: 2}
		var c = {a: 3, b: 6, c: 1}

		expect(Module.addObjects(a, b)).toEqual(c);
	});

	it("should add two complex objects", function(){
		var a = {a: 3, b: {e: 1}}
		var b = {c: [1], b: {r: 4, e: 2}}
		var c = {a: 3, b: {r: 4, e: 3}, c: [1]}

		expect(Module.addObjects(a, b)).toEqual(c);
	});

	it("the test database should be ok", function(){
		TestCollection.insert({a: 'a', b: 'b'});

		expect(TestCollection.find().count()).toBe(1);
	});
});

