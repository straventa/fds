import moment from "moment";

export class TimeUtil {
  static formatTime(date: Date | null | undefined): string {
    if (!date) {
      return "N/A";
    }
    return `${moment(date).format("MMMM D, YYYY [at] h:mm A")}`;
  }
}
