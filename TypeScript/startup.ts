interface Startup {
  name: string;
  foundedYear: number;
  totalFunding: number;
  isPublic: boolean;
}
const startup1: Startup = {
  name: "TechNova",
  foundedYear: 2010,
  totalFunding: 5000000,
  isPublic: false
};
const startup2: Startup = {
  name: "CloudX",
  foundedYear: 2018,
  totalFunding: 2000000,
  isPublic: true
};
function isEstablished(startup: Startup): boolean {
  return startup.foundedYear < 2015;
}
console.log(isEstablished(startup1)); 
console.log(isEstablished(startup2)); 