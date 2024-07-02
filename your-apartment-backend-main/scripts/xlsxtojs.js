const readline = require('readline')
const fs = require('node:fs');

const APT_SIZE = 18744

function read(filepath) {
	const lines = fs.readFileSync(filepath).toString().split('\n')
	return lines.map((val) => val.trim())
}



async function main() {
	console.log("read id.txt...")
	const id = read("id.txt")

	console.log("read region.txt...")
	const regionNames = read("region.txt")

	console.log("read apt.txt...")
	const apartmentNames = read('apt.txt')

	console.log("read address.txt...")
	const address = read('address.txt')
	
	let succ = 0
	let fail = 0
	fs.writeFileSync("output.sql", "")
	for (let i = 0; i < APT_SIZE; ++i) {
		try {
			fs.appendFileSync("output.sql", `INSERT INTO apartment_name (_id, apartment_name, address, region_name) VALUES("${id[i]}", "${apartmentNames[i]}", "${address[i]}", "${regionNames[i]}");\n`)
			console.log(`succ: ${++succ}, fail: ${fail}`)
		} catch(ex) {
			console.error(ex)
			console.log(`succ: ${succ}, fail: ${++fail}`)
		}
	}
	console.log('export ok.')
}

main().then()