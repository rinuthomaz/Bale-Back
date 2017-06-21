USE [BRTADEV]
GO

ALTER TABLE [dbo].[Image] DROP CONSTRAINT [FK_Image_BalePickupTrip]
GO

/****** Object:  Table [dbo].[Image]    Script Date: 12/29/2016 6:11:50 PM ******/
DROP TABLE [dbo].[Image]
GO

/****** Object:  Table [dbo].[Image]    Script Date: 12/29/2016 6:11:50 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Image](
	[ImageUId] [int] NOT NULL,
	[TripId] [int] NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[Enabled] [bit] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](50) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[Image]  WITH CHECK ADD  CONSTRAINT [FK_Image_BalePickupTrip] FOREIGN KEY([TripId])
REFERENCES [dbo].[BalePickupTrip] ([TripId])
GO

ALTER TABLE [dbo].[Image] CHECK CONSTRAINT [FK_Image_BalePickupTrip]
GO

