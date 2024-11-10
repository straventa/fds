export function convertStringToTime(timeString: string): string {
  try {
    return `${timeString.slice(0, 2)}:${timeString.slice(
      2,
      4
    )}:${timeString.slice(4, 6)}`;
  } catch (error) {
    console.log("error", error);
    return "";
  }
}
