USE [BRTADEV]
GO

ALTER TABLE [dbo].[BalePickupAssignment] DROP CONSTRAINT [FK_BalePickupAssignment_User]
GO

ALTER TABLE [dbo].[BalePickupAssignment] DROP CONSTRAINT [FK_BalePickupAssignment_CustomerSite1]
GO

ALTER TABLE [dbo].[BalePickupAssignment] DROP CONSTRAINT [FK_BalePickupAssignment_CustomerSite]
GO

/****** Object:  Table [dbo].[BalePickupAssignment]    Script Date: 12/29/2016 6:01:00 PM ******/
DROP TABLE [dbo].[BalePickupAssignment]
GO

/****** Object:  Table [dbo].[BalePickupAssignment]    Script Date: 12/29/2016 6:01:00 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[BalePickupAssignment](
	[BalePickupAssignmentId] [int] NOT NULL,
	[UserId] [int] NOT NULL,
	[BuyCustomerSiteId] [int] NOT NULL,
	[BuyLegacyVendorId] [nvarchar](50) NULL,
	[FrequencyId] [int] NULL,
	[SellCustomerSiteId] [int] NOT NULL,
	[SellLegacyVendorId] [nvarchar](50) NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[Enabled] [bit] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_BalePickupAssignment] PRIMARY KEY CLUSTERED 
(
	[BalePickupAssignmentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[BalePickupAssignment]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupAssignment_CustomerSite] FOREIGN KEY([BuyCustomerSiteId])
REFERENCES [dbo].[CustomerSite] ([CustomerSiteId])
GO

ALTER TABLE [dbo].[BalePickupAssignment] CHECK CONSTRAINT [FK_BalePickupAssignment_CustomerSite]
GO

ALTER TABLE [dbo].[BalePickupAssignment]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupAssignment_CustomerSite1] FOREIGN KEY([SellCustomerSiteId])
REFERENCES [dbo].[CustomerSite] ([CustomerSiteId])
GO

ALTER TABLE [dbo].[BalePickupAssignment] CHECK CONSTRAINT [FK_BalePickupAssignment_CustomerSite1]
GO

ALTER TABLE [dbo].[BalePickupAssignment]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupAssignment_User] FOREIGN KEY([UserId])
REFERENCES [dbo].[User] ([UserId])
GO

ALTER TABLE [dbo].[BalePickupAssignment] CHECK CONSTRAINT [FK_BalePickupAssignment_User]
GO

