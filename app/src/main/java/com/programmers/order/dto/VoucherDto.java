package com.programmers.order.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
	public static class Resolver {
		private UUID id;
		private long discountValue;
		private LocalDateTime createdAt;

		public Resolver(UUID id, long discountValue, LocalDateTime createdAt) {
			this.id = id;
			this.discountValue = discountValue;
			this.createdAt = createdAt;
		}

		public UUID getId() {
			return id;
		}

		public long getDiscountValue() {
			return discountValue;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
	}

	public static class Response {
		private UUID id;
		private long discountValue;
		private LocalDateTime createdAt;

		public Response(UUID id, long discountValue, LocalDateTime createdAt) {
			this.id = id;
			this.discountValue = discountValue;
			this.createdAt = createdAt;
		}

		public UUID getId() {
			return id;
		}

		public String show() {
			return "id=" + id +
					", discountValue=" + discountValue +
					", createdAt=" + createdAt;
		}

		@Override
		public String toString() {
			return "Response{" +
					"id=" + id +
					", discountValue=" + discountValue +
					", createdAt=" + createdAt +
					'}';
		}
	}

}
