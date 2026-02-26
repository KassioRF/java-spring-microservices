export const NewSaleStatus = {
  OPEN: "OPEN",
  PAID: "PAID",
};

export type NewSaleStatus = (typeof NewSaleStatus)[keyof typeof NewSaleStatus];

export const SaleStatus = {
  OPEN: "OPEN",
  PAID: "PAID",
  CANCELLED: "CANCELLED",
  REFUNDED: "REFUNDED",
};

export type SaleStatus = (typeof SaleStatus)[keyof typeof SaleStatus];
