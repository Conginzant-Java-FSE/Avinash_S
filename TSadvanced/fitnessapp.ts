interface Subscription {
  plan?: string;
  expiryDate?: string;
}
function upgradeSubscription(
  sub: Subscription
): Required<Subscription> {
  if (!sub.plan || !sub.expiryDate) {
    throw new Error("Missing subscription details");
  }
  return {
    plan: sub.plan,
    expiryDate: sub.expiryDate
  };
}
console.log(
  upgradeSubscription({
    plan: "Premium",
    expiryDate: "2026-01-01"
  })
);