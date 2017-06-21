USE [BRTADEV]
GO

ALTER TABLE [dbo].[Supplier] DROP CONSTRAINT [FK_Supplier_Supplier]
GO

/****** Object:  Table [dbo].[Supplier]    Script Date: 12/29/2016 4:39:22 PM ******/
DROP TABLE [dbo].[Supplier]
GO

/****** Object:  Table [dbo].[Supplier]    Script Date: 12/29/2016 4:39:22 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Supplier](
	[SupplierId] [int] NOT NULL,
	[CompanyOutletId] [int] NULL,
	[Description] [nvarchar](225) NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_Supplier] PRIMARY KEY CLUSTERED 
(
	[SupplierId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[Supplier]  WITH CHECK ADD  CONSTRAINT [FK_Supplier_Supplier] FOREIGN KEY([CompanyOutletId])
REFERENCES [dbo].[CompanyOutlet] ([CompanyOutletId])
GO

ALTER TABLE [dbo].[Supplier] CHECK CONSTRAINT [FK_Supplier_Supplier]
GO

