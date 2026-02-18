interface MobileApp {
  name: string;
  developer: string;
  downloads: number;
  rating?: number;
  isPremium: boolean;
}
const app1: MobileApp = {
  name: "ChatApp",
  developer: "TechSoft",
  downloads: 50000,
  rating: 4.7,
  isPremium: false
};
const app2: MobileApp = {
  name: "GamePro",
  developer: "FunGames",
  downloads: 100000,
  isPremium: true
};
const app3: MobileApp = {
  name: "MusicPlus",
  developer: "SoundWave",
  downloads: 75000,
  rating: 4.3,
  isPremium: false
};
function isHighlyRated(app: MobileApp): boolean {
  return app.rating !== undefined && app.rating >= 4.5;
}
console.log(isHighlyRated(app1)); 
console.log(isHighlyRated(app2)); 
console.log(isHighlyRated(app3)); 
