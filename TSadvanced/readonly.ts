function sortLeaderboard<T extends { score: number }>(
  players: readonly T[]
): T[] {
  return [...players].sort((a, b) => b.score - a.score);
}
const leaderboard = [
  { name: "Alex", score: 120 },
  { name: "Mira", score: 200 }
] as const;
console.log(sortLeaderboard(leaderboard));
