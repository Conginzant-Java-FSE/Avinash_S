interface UserProfile {
  name: string;
  balance: number;
  verified: boolean;
}
function updateProfile(
  profile: UserProfile,
  updates: Partial<UserProfile>
): UserProfile {
  return { ...profile, ...updates };
}
const profile = {
  name: "Aarav",
  balance: 5000,
  verified: false
};
console.log(updateProfile(profile, { verified: true }));
