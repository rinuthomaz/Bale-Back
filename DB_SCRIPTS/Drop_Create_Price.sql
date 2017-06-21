USE [BRTADEV]
GO

ALTER TABLE [dbo].[Price] DROP CONSTRAINT [FK_Price_Material]
GO

/****** Object:  Table [dbo].[Price]    Script Date: 12/29/2016 4:41:52 PM ******/
DROP TABLE [dbo].[Price]
GO

/****** Object:  Table [dbo].[Price]    Script Date: 12/29/2016 4:41:52 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Price](
	[PriceId] [int] NOT NULL,
	[MaterialId] [int] NULL,
	[Price] [decimal](18, 4) NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_Price] PRIMARY KEY CLUSTERED 
(
	[PriceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[Price]  WITH CHECK ADD  CONSTRAINT [FK_Price_Material] FOREIGN KEY([MaterialId])
REFERENCES [dbo].[Material] ([MaterialId])
GO

ALTER TABLE [dbo].[Price] CHECK CONSTRAINT [FK_Price_Material]
GO

