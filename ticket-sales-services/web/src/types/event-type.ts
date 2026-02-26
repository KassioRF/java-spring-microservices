export const EventType = {
  PALESTRA: "PALESTRA",
  SHOW: "SHOW",
  TEATRO: "TEATRO",
  CURSO: "CURSO",
  GERAL: "GERAL",
};

export type EventType = (typeof EventType)[keyof typeof EventType];
