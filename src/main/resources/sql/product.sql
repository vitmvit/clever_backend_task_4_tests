DROP TABLE IF EXISTS "product";
DROP SEQUENCE IF EXISTS product_uuid_seq;
CREATE SEQUENCE product_uuid_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

CREATE TABLE "public"."product"
(
    "uuid"        text DEFAULT 'nextval(''product_uuid_seq'')' NOT NULL,
    "name"        text                                         NOT NULL,
    "description" text                                         NOT NULL,
    "price"       numeric                                      NOT NULL,
    "created"     timestamp                                    NOT NULL,
    CONSTRAINT "product_pkey" PRIMARY KEY ("uuid")
) WITH (oids = false);