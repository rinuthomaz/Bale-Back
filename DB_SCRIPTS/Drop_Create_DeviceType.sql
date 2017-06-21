USE [BRTADEV]
GO

/****** Object:  Table [dbo].[DeviceType]    Script Date: 12/21/2016 1:55:03 PM ******/
DROP TABLE [dbo].[DeviceType]
GO

/****** Object:  Table [dbo].[DeviceType]    Script Date: 12/21/2016 1:55:03 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[DeviceType](
	[DeviceTypeId] [int] NOT NULL,
	[DeviceDescription] [nvarchar](50) NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[Enabled] [bit] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_DeviceType] PRIMARY KEY CLUSTERED 
(
	[DeviceTypeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


