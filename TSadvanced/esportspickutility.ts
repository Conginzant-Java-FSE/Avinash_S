interface PlayerStats {
  name: string;
  goals: number;
  assists: number;
  yellowCards: number;
}
function getOffensiveStats(
  player: PlayerStats
): Pick<PlayerStats, "goals" | "assists"> {
  return {
    goals: player.goals,
    assists: player.assists
  };
}
console.log(
  getOffensiveStats({
    name: "Ronaldo",
    goals: 3,
    assists: 1,
    yellowCards: 2
  })
);
