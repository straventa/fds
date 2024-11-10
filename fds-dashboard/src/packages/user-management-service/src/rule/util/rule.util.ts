export function getRiskLevelColor(riskLevel: string) {
  if (riskLevel.toLocaleLowerCase() === "high risk") {
    return "red";
  } else if (riskLevel.toLocaleLowerCase() === "medium risk") {
    return "yellow";
  } else if (riskLevel.toLocaleLowerCase() === "low risk") {
    return "green";
  }
}
