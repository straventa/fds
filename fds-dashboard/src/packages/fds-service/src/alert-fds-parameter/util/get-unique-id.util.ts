import type { AlertFdsParameterDto } from "@/packages/fds-service/src/alert-fds-parameter/dto/alert-fds-parameter.dto";

export function getUniqueIdUtil({
  alertFdsParameter,
}: {
  alertFdsParameter: AlertFdsParameterDto;
}) {
  return `${alertFdsParameter.authSeqNo},${alertFdsParameter.authDate},${alertFdsParameter.cardNo}`;
}
